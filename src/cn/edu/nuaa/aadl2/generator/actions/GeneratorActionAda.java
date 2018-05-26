package cn.edu.nuaa.aadl2.generator.actions;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;

//import org.eclipse.cdt.internal.core.model.CProject;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.osate.aadl2.Classifier;
import org.osate.aadl2.ComponentImplementation;
import org.osate.aadl2.ContainedNamedElement;
import org.osate.aadl2.ContainmentPathElement;
import org.osate.aadl2.DataClassifier;
import org.osate.aadl2.DataSubcomponent;
import org.osate.aadl2.ListValue;
import org.osate.aadl2.ModalPropertyValue;
import org.osate.aadl2.ProcessClassifier;
import org.osate.aadl2.ProcessImplementation;
import org.osate.aadl2.ProcessSubcomponent;
import org.osate.aadl2.ProcessorClassifier;
import org.osate.aadl2.ProcessorImplementation;
import org.osate.aadl2.ProcessorSubcomponent;
import org.osate.aadl2.Property;
import org.osate.aadl2.PropertyAssociation;
import org.osate.aadl2.PropertyExpression;
import org.osate.aadl2.PublicPackageSection;
import org.osate.aadl2.ReferenceValue;
import org.osate.aadl2.Subcomponent;
import org.osate.aadl2.SystemImplementation;
import org.osate.aadl2.impl.PublicPackageSectionImpl;
import org.osate.aadl2.instance.ComponentInstance;
import org.osate.aadl2.instance.SystemInstance;
import org.osate.aadl2.instance.impl.SystemInstanceImpl;
import org.osate.aadl2.modelsupport.resources.OsateResourceUtil;
import org.osate.workspace.IAadlElement;
import org.osate.workspace.IAadlProject;

import cn.edu.nuaa.aadl2.generator.utils.PropertyUtils;
import cn.edu.nuaa.aadl2.generator.utils.Tools;
import cn.edu.nuaa.aadl2.generator.workflow.GenerateAda;


//public class GeneratorAction extends Convert2CprjAbstractHandler implements IObjectActionDelegate {
public class GeneratorActionAda implements IObjectActionDelegate {
	/** */
	private Shell shell;
	/** */
	private static String projectname;
	/** */
	private SystemInstance _si = null;
	/** */
	private static MessageConsole console = new MessageConsole("", null);
	/** */
	static boolean exists = false;
	/** */
	private static IProject cproject;
	
	
	
	/**
	 * Constructor for Action1.
	 */
	public GeneratorActionAda() {
		super();
	}
	
	
	
	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}
	

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
//		GeneratorActionAda.getConsole().clearConsole();
		preCprjCreate();
		
		MessageDialog.openInformation(
				shell,
				"从AADL模型生成Ada代码",
				"生成完毕");
	}
	
	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 * 鼠标选择监听
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		if ((!(selection.isEmpty()))
				&& (selection instanceof IStructuredSelection)) {
			Object obj = ((IStructuredSelection) selection).getFirstElement();
			// System.out.println(obj instanceof IFile);
			if (obj instanceof IFile) {
				final IFile file = (IFile) obj;
				if (file.getFileExtension().toLowerCase().equals("aaxl2")) {
					org.osate.aadl2.instance.SystemInstance model;
					XtextResourceSet resourceSet = OsateResourceUtil
							.getResourceSet();
					String sp = file.getFullPath().toString();
					Resource resource = resourceSet.getResource(
							URI.createPlatformResourceURI(sp, false), true);
					if (resource.getContents().size() > 0) {
						model = (org.osate.aadl2.instance.SystemInstance) resource
								.getContents().get(0);
						_si = model;
					} else {
						model = null;
					}
					// System.out.println(model);
				}

			}
		}
	}

	public static IProject getCurrentProject() {
		@SuppressWarnings("restriction")
		ISelectionService selectionService = Workbench.getInstance()
				.getActiveWorkbenchWindow().getSelectionService();
		
		ISelection selection = selectionService.getSelection();

		IProject project = null;
		if (selection instanceof IStructuredSelection) {
			Object element = ((IStructuredSelection) selection)
					.getFirstElement();

			if (element instanceof IResource) {
				project = ((IResource) element).getProject();
			} else if (element instanceof IAadlProject) {
				IAadlProject jProject = ((IAadlElement) element)
						.getAadlProject();
				project = jProject.getProject();
			}
		}
		return project;
	}

	SystemImplementation getSystemImplementation2(SystemInstanceImpl _si){
		for(ComponentImplementation sysi : _si.getInstantiatedObjects()){
			if(sysi instanceof SystemImplementation){
				return (SystemImplementation)sysi;
			}
		}
		return null;
	}

	/**
	 * systemInstanceImpl和systemImplementation的关系？？？？
	 * Each SystemImplementation is a ComponentImplementation of SystemInstanceImpl 
	 * GetInstantiatedObjects() returns a list of ComponentImplementation
	 *  */
	public void preCprjCreate() {
		
		GenerateAda.generate(getSystemImplementation2((SystemInstanceImpl)_si));
		try {
			getCurrentProject().refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
