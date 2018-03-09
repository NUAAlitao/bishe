#include "vxworks_gnc.h"
int ticks_pass;
void time_cpu()
{
	int i=0;
	int j=0;
	for(i=0;i<7654321*5;i++);
		//for(j=0;j<654321;j++);
}
/*void task_schedule()
{
	WIND_TCB *pTcb;
	while(1)
	{
		taskSuspend(0);
		if(task_manage_finish==0)
		{
			pTcb=task_manage_tcb;
			taskRegsInit(pTcb,pTcb->pStackBase);
			task_manage_finish==1;
		}
		else if(task_control_finish==0)
		{
			pTcb=task_control_tcb;
			taskRegsInit(pTcb,pTcb->pStackBase);
			task_control_finish==1;
		}
		else if(task_telemetry_finish==0)
		{
			pTcb=task_telemetry_tcb;
			taskRegsInit(pTcb,pTcb->pStackBase);
			task_telemetry_finish==1;
		}
	}
}*/
void timer_schedule_handler(int pa)
{
	WIND_TCB *pTcb;
 	ticks_pass++;
	time_current++;
 	time_current=(time_current+time_cycle)%time_cycle;
	//myprintf1("time_current=%d\n",time_current);
	if(time_manage==time_current)
	{
		if(ticks_pass>=100*60*60*8)
		{
			wdDelete(timer_schedule);
			wdDelete(timer_telemetry);
			test_print();
			printf("all finish\n");
			return;
		}	
		if(task_manage_finish==0)
		{
		//	taskResume((int)task_schedule_tcb);
			//task_manage_tcb->status=WIND_SUSPEND;
			taskSuspend(task_manage_tcb);
			//myprintf1("task_manage not finish\n");
		}
		if(task_control_finish==0)
		{

			pTcb=task_control_tcb;
			taskRegsInit(pTcb,pTcb->pStackBase);
			taskResume(pTcb);
			task_control_finish==1;
		}
		set_system_time(system_cycle_flags);
//		myprintf1("time_current=%d\n",time_current);
		semGive(sem_control);
	}
	else if(time_control==time_current)
	{
		if(task_control_finish==0)
		{
			//taskResume((int)task_schedule_tcb);
			taskSuspend(task_control_tcb);
			//myprintf1("task_control not finish\n");
		}
		if(task_telemetry_finish==0)
		{
			pTcb=task_telemetry_tcb;
			taskRegsInit(pTcb,pTcb->pStackBase);
			taskResume(pTcb);
			task_telemetry_finish==1;
		}
		semGive(sem_telemetry);
	}
	else if(time_telemetry==time_current)
	{
//		myprintf1("time_current=%d\n",time_current);
		if(task_telemetry_finish==0)
		{
			//taskResume((int)task_schedule_tcb);
			taskSuspend(task_telemetry_tcb);
			//myprintf1("task_telemetry not finish\n");
		}
		if(task_manage_finish==0)
		{
			pTcb=task_manage_tcb;
			taskRegsInit(pTcb,pTcb->pStackBase);
			taskResume(pTcb);
			task_manage_finish==1;
		}	
	//	myprintf1("status=%d\n",task_manage_tcb->status);	
		semGive(sem_manage);
	}
    wdStart(timer_schedule,1,timer_schedule_handler,0);
}
void timer_telemetry_handler(int pa)
{
	__asm__ __volatile__("int $0x81");
	wdStart(timer_telemetry,51,timer_telemetry_handler,0);
}
void int_telemetry_handler()
{
	flag_frame_arrived=1;
}
void system_data_init()
{
	ticks_pass=0;
	time_control=13;
	time_telemetry=14;
	time_manage=0;;
	time_cycle=16;
	time_current=-1;
	task_control_finish=1;
	task_telemetry_finish=1;
	task_manage_finish=1;
	
	current_count_n1=30;
	current_count_n2=30;
	system_cycle_flags=1;
	task_control_failure_count=0;
	task_telemetry_failure_count=0;
	task_manage_failure_count=0;
	flag_frame_arrived=0;
	
}
void init_system_task()
{
	system_data_init();
	sysClkRateSet(100);
	intConnect(INUM_TO_IVEC(0x81),(VOIDFUNCPTR)int_telemetry_handler,0);	
	timer_schedule=wdCreate();
	wdStart(timer_schedule,1,timer_schedule_handler,0);
	timer_telemetry=wdCreate();
	wdStart(timer_telemetry,51,timer_telemetry_handler,0);
	
	sem_control=semBCreate(SEM_Q_PRIORITY,SEM_EMPTY);
	sem_telemetry=semBCreate(SEM_Q_PRIORITY,SEM_EMPTY);
	sem_manage=semBCreate(SEM_Q_PRIORITY,SEM_EMPTY);
	
	task_control_tcb=(WIND_TCB *)taskSpawn("task_control",25,0,STACK_SIZE,(FUNCPTR)task_control,0,0,0,0,0,0,0,0,0,0);
	task_telemetry_tcb=(WIND_TCB *)taskSpawn("task_telemetry",26,0,STACK_SIZE,(FUNCPTR)task_telemetry,0,0,0,0,0,0,0,0,0,0);
	task_manage_tcb=(WIND_TCB *)taskSpawn("task_manage",27,0,STACK_SIZE,(FUNCPTR)task_manage,0,0,0,0,0,0,0,0,0,0);
	//task_schedule_tcb=taskSpawn("task_schedule",24,0,STACK_SIZE,(FUNCPTR)task_schedule,0,0,0,0,0,0,0,0,0,0);
	taskSpawn("task_idle",28,0,STACK_SIZE,(FUNCPTR)task_idle,0,0,0,0,0,0,0,0,0,0);
	taskSuspend(0);
}
void task_idle()
{
	int i=0;
	while(1)
	{
		for(i=0;i<1654321;i++);
	//	myprintf1("in task_idle,i=%d\n",i);
	//	myprintf1("in idle status=%d\n",task_manage_tcb->status);	
	}
}
void task_control()
{
	int i=0;
	while(1)
	{
		semTake(sem_control,-1);
		task_control_finish=0;
		//myprintf1("in task_control\n");
		set_work_mode();
		data_gather();
		data_handler();
		rail_control();
		error_handler();
		if(ticks_pass%64==1)
		{
			for(i=0;i<200;i++)
				time_cpu();
		}
		task_control_finish=1;
		//myprintf1("exit task_control\n");
	}
}
void set_work_mode()//设置工作模式
{
	if(ticks_pass>=2*60*60*100&&ticks_pass<5*60*60*100&&system_cycle_flags==1)
	{
		system_cycle_flags=0;
	}
	else
	if(ticks_pass>=5*60*60*100&&system_cycle_flags==0)
		{
			system_cycle_flags=1;
		}
}
void data_gather()//数据采集
{
	ship_time_data.len=strlen("ship_time_data")+1;
	strcpy(ship_time_data.buf,"ship_time_data");
	floated_imu_data.len=strlen("floated_imu_data")+1;
	strcpy(floated_imu_data.buf,"floated_imu_data");
	control_imu_data.len=strlen("control_imu_data")+1;
	strcpy(control_imu_data.buf,"control_imu_data");
	infrared_data.len=strlen("infrared_data")+1;
	strcpy(infrared_data.buf,"infrared_data");
	laser_radar_data.len=strlen("laser_radar_data")+1;
	strcpy(laser_radar_data.buf,"laser_radar_data");
	instrument_data.len=strlen("instrument_data")+1;
	strcpy(instrument_data.buf,"instrument_data");
	imitate_sun_data.len=strlen("imitate_sun_data")+1;
	strcpy(imitate_sun_data.buf,"imitate_sun_data");

}
void data_handler()//数据处理
{
}
void rail_control()//姿态和轨道控制
{
}
void error_handler()//故障诊断和处理
{
}
void task_telemetry()
{
	while(1)
	{
		semTake(sem_telemetry,-1);
		task_telemetry_finish=0;
		//myprintf1("in task_telemetry\n");
		get_telemetry_data();
		send_telemetry_data();
		task_telemetry_finish=1;
		//myprintf1("exit task_telemetry\n");
	}
}
void get_telemetry_data()//获取遥测数据包
{
	if(flag_frame_arrived==1)
	{
		memcpy(ram_tmp_buffer1.buf,multi_ram_buffer.buf,current_count_n1);
		ram_tmp_buffer1.len=current_count_n1;
		memcpy(ram_tmp_buffer2.buf,&(multi_ram_buffer.buf[current_count_n1]),72-current_count_n1);
		ram_tmp_buffer2.len=72-current_count_n1;
		memcpy(ram_tmp_buffer2.buf,ram_tmp_buffer1.buf,ram_tmp_buffer1.len);
		flag_frame_arrived=0;
	}
	else
	{
		memcpy(ram_tmp_buffer1.buf,multi_ram_buffer.buf,current_count_n2);
		ram_tmp_buffer1.len=current_count_n2;
	}
}
void send_telemetry_data()//发送遥测数据包
{
	display_buffer.len=ram_tmp_buffer2.len;
	memcpy(display_buffer.buf,ram_tmp_buffer2.buf,ram_tmp_buffer2.len);
}
void task_manage()
{
	while(1)
	{	
		semTake(sem_manage,-1);
		task_manage_finish=0;
		//myprintf1("in task_manage\n");
	//	set_system_time(system_cycle_flags);
		check_task_finish();
		check_task_stack();
		task_manage_finish=1;
		//myprintf1("exit task_manage\n");
	}
}
void set_system_time(short flags)//设置系统控制周期
{
	if(flags==1&&time_cycle==10)
	{
		time_cycle=16;
		time_control=13;
		time_telemetry=14;
		time_manage=0;
	}
	else if(flags==0&&time_cycle==16)
	{
		time_cycle=10;
		time_control=7;
		time_telemetry=8;
		time_manage=0;
	}
}
void check_task_finish()//任务完成检查
{
 if(task_control_finish==0)
 task_control_failure_count++;
 if(task_telemetry_finish==0)
 task_telemetry_failure_count++;
 if(task_manage_finish==0)
 task_manage_failure_count++;
}
void check_task_stack()//任务堆栈检查
{
}
int myoutput(char ch,int com)
{
	//靠靠靠靠靠靠?
	SIO_CHAN*pSioChan = sysSerialChanGet(com);//获取COM1
	int ret = -1;
	while(ret != 0)
		ret = (*pSioChan->pDrvFuncs->pollOutput)(pSioChan,ch);//send a char
}

void myprintf1(const char* fmt,...)
{
	va_list vaList;	/* traverses argument list */
    	int nChars;
	char buffer[1024];
    	va_start (vaList, fmt);
    	nChars = vsprintf (buffer,fmt, vaList);
    	va_end (vaList);
	char* str = buffer;
	while(*str)
		myoutput(*str++,0);
}
