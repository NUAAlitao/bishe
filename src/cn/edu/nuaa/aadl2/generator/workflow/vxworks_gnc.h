#include <semLib.h>
#include "vxWorks.h"
#include <sigevent.h>
#include <msgQLib.h>
#include <tickLib.h>
#include <wdLib.h>
#include <time.h>
#include <signal.h>
#include <stdio.h>
#include <ioLib.h>
#include <sysLib.h>
#include <stdarg.h>
#include <intLib.h>
#include <taskLib.h>
#include "private/taskLibP.h"
#include <arch/i86/ivI86.h>
#include <test.h>
#define STACK_SIZE 20480
#define MAX_LEN 100
typedef struct //内存缓冲区的格式
{
int len;
char buf[MAX_LEN];
}BUFFER;
/*task declain*/
void task_control();
void task_telemetry();
void task_manage();
void task_idle();

/*task tcb declain*/
WIND_TCB *task_control_tcb;
WIND_TCB *task_telemetry_tcb;
WIND_TCB *task_manage_tcb;


/*sem declain*/
SEM_ID  sem_control;
SEM_ID  sem_telemetry;
SEM_ID 	sem_manage;

WDOG_ID timer_schedule;
WDOG_ID timer_telemetry;
//extern void	vxTaskEntry ();
/*timer handler*/
void timer_schedule_handler(int pa);
void timer_telemetry_handler(int pa);

void int_telemetry_handler();

short flag_frame_arrived;//帧标志
BUFFER multi_ram_buffer;//size=72双口ram缓存区
BUFFER ram_tmp_buffer1,ram_tmp_buffer2;//size=72临时缓冲区
BUFFER display_buffer;//仪表显示缓冲区
int current_count_n1, current_count_n2;//当前路计数
/* 采集的数据*/
BUFFER ship_time_data;//船时数据
BUFFER floated_imu_data;//浮液imu数据
BUFFER control_imu_data;//控制imu数据
BUFFER infrared_data;//红外数据
BUFFER laser_radar_data;//激光雷达数据
BUFFER instrument_data;//仪表编码指令板数据
BUFFER imitate_sun_data;//模拟太阳数据

short system_cycle_flags;//系统周期标志，在控制任务中设置
int time_control;//控制任务完成执行的时间
int time_telemetry;//遥测任务完成执行的时间
int time_manage;//管理任务执行完成的时间
int time_cycle;//系统周期
int time_current;//当前的时间
short task_control_finish;//0表示控制任务没有完成，1表示完成
short task_telemetry_finish;//意义同上
short task_manage_finish;//意义同上

int task_control_failure_count;//控制任务失败（未执行完）次数
int task_telemetry_failure_count;//遥测任务失败次数
int task_manage_failure_count;//管理任务失败次数

/*function in task_control*/
void set_work_mode();//设置工作模式
void data_gather();//数据采集
void data_handle();//数据处理
void rail_control();//姿态和轨道控制
void error_handle();//故障诊断和处理

/*function in task_telemetry*/
void get_telemetry_data();//获取遥测数据包
void send_telemetry_data();//发送遥测数据包

/* function in task_manage*/
void set_system_time(short flags);//设置系统控制周期
void check_task_finish();//任务完成检查
void check_task_stack();//任务堆栈检查

void system_data_init();//系统全局变量的初始化


