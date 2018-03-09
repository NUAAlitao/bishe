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
		GeneratorActionC.getConsole().clearConsole();

			//Generate.generate(_si.getSystemImplementation());
			preCprjCreate();

	}
	
	/** */
//	public void run(IAction action) {
//		GeneratorAction.getConsole().clearConsole();
//		if (_si != null) {
//			SystemImplementation si = _si.getSystemImplementation();
////			final Display d = PlatformUI.getWorkbench().getDisplay();
////			d.syncExec(new Runnable(){
////
////				public void run() {
////					IWorkbenchWindow window;
////					Shell sh;
////					
////					window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
////					sh = window.getShell();
////					
////					InputDialog fd = new InputDialog(sh, "Error State name", "Please specify the name of the error state name\n(with the optional error type separated by a space)", "failed", null);
////					if (fd.open() == Window.OK)
////					{
////						projectname = fd.getValue();
////					}
////					else
////					{
////						projectname = null;
////					}
////
////						
////			}});
////			printToConsole(projectname);
//			try{
//				cproject=(IProject) execute(null);
//			} catch (ExecutionException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			//Generate.generate(_si.getSystemImplementation());
//			preCprjCreate();
//			postCprjCreate(cproject);
//			
//		} else {
//			// printToConsole("model is null");
//
//		}
//		_si = null;
//
//	}

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

	public void openConsole() {
		showConsole();
	}

	private static void showConsole() {
		if (console != null) {
			IConsoleManager manager = ConsolePlugin.getDefault()
					.getConsoleManager();

			IConsole[] existing = manager.getConsoles();
			exists = false;
			for (int i = 0; i < existing.length; i++) {
				if (console == existing[i])
					exists = true;
			}
			if (!exists) {
				manager.addConsoles(new IConsole[] { console });
			}
		}
	}

	public static void closeConsole() {
		IConsoleManager manager = ConsolePlugin.getDefault()
				.getConsoleManager();
		if (console != null) {
			manager.removeConsoles(new IConsole[] { console });
		}
	}

	public static MessageConsole getConsole() {

		showConsole();

		return console;
	}

	public static void printToConsole(String message, boolean activate) {
		MessageConsoleStream printer = GeneratorActionC.getConsole()
				.newMessageStream();
		printer.setActivateOnWrite(activate);
		printer.println("��Ϣ" + message);
	}

	public static void printToConsole(String message) {
		MessageConsoleStream printer = GeneratorActionC.getConsole()
				.newMessageStream();
		printer.setActivateOnWrite(true);
		printer.println(message);
	}

	public static void printToConsole(Object message) {

		MessageConsoleStream printer = GeneratorActionC.getConsole()
				.newMessageStream();

		printer.setActivateOnWrite(true);
		if (message != null)
			printer.println(message.toString());
		else
			printer.println("null");
	}

	public static void printToAllConsole(Object message) {
		MessageConsoleStream printer = GeneratorActionC.getConsole()
				.newMessageStream();
		printer.setActivateOnWrite(true);
		if (message != null) {
			printer.println(message.toString());
			java.lang.System.out.println(message.toString());
		} else {
			printer.println("null");
			java.lang.System.out.println("null");
		}
	}

	public static void printToAllConsole() {
		MessageConsoleStream printer = GeneratorActionC.getConsole()
				.newMessageStream();
		printer.setActivateOnWrite(true);
		printer.println();
		java.lang.System.out.println();
	}

	public static void printToConsole() {
		MessageConsoleStream printer = GeneratorActionC.getConsole()
				.newMessageStream();
		printer.setActivateOnWrite(true);
		printer.println();

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

	public static void createFolderInProject(IProject project, String folderName) {

		IFolder newFolder = project.getFolder(folderName);
		if (!newFolder.exists()) {
			try {
				newFolder.create(true, true, null);
			} catch (CoreException e) {
				// ... ...
				// Deal with Operation

			}
		} else {
			try {
				newFolder.delete(true, null);
				newFolder.create(true, true, null);
			} catch (CoreException e) {
				e.printStackTrace();
			}

		}
	}

	public static void createFolderAndFileInProject(IProject project,
			String folderName, String fileName) {

		IFolder newFolder = project.getFolder(folderName);
		if (!newFolder.exists()) {
			try {
				newFolder.create(true, true, null);
			} catch (CoreException e) {
				// ... ...
				// Deal with Operation
			}
		}
		createFileInFolder(newFolder, fileName);
	}

	public static void createFileInFolder(IFolder folder) {
		IFile newFile = folder.getFile("new_File.txt");
		try {
			File systemFile = null;
			if (newFile.exists())
				newFile.setContents(getInitialContents(), true, false, null);
			else
				systemFile = newFile.getLocation().toFile();

			if (systemFile.exists()) {
				// skip create -- in file system
				// could refreshLocal on parent at this point
				newFile.create(getInitialContents(), false, null);
			} else {
				newFile.create(getInitialContents(), false, null);
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	public static void createFileInFolder(IFolder folder, String fileName) {
		IFile newFile = folder.getFile(fileName);
		try {
			File systemFile = null;
			if (newFile.exists())
				newFile.setContents(getInitialContents(), true, false, null);
			else
				systemFile = newFile.getLocation().toFile();

			if (systemFile.exists()) {
				// skip create -- in file system
				// could refreshLocal on parent at this point
				newFile.create(getInitialContents(), false, null);
			} else {
				newFile.create(getInitialContents(), false, null);
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	// Return input stream used to create initial file contents.
	public static InputStream getInitialContents() {
		StringBuffer sb = new StringBuffer();
		sb.append("My New File Contents");
		return new ByteArrayInputStream(sb.toString().getBytes());
	}

	public static void createFileInFolder(IFolder folder, String fileName,
			InputStream input) {
		IFile newFile = folder.getFile(fileName);
		try {
			File systemFile = null;
			if (newFile.exists())
				newFile.setContents(input, true, false, null);
			else
				systemFile = newFile.getLocation().toFile();

			if (systemFile.exists()) {
				// skip create -- in file system
				// could refreshLocal on parent at this point
				newFile.create(input, false, null);
			} else {
				newFile.create(input, false, null);
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	public static void createFileAndWriteContentInCorrespondFolder(
			String folderName, String fileName, InputStream input) {
		String[] folders = folderName.split("\\.");
		IProject project = getCurrentProject();
		if (folders.length == 2) {
			IFolder systemFolder = project.getFolder(folders[0]);
			if (systemFolder.exists()) {
				IFolder siFolder = systemFolder.getFolder(folders[1]);
				if (siFolder.exists()) {
					IFile newFile = siFolder.getFile(fileName);
					try {
						File systemFile = null;
						if (newFile.exists())
							newFile.setContents(input, true, false, null);
						else
							systemFile = newFile.getLocation().toFile();

						if (systemFile.exists()) {
							// skip create -- in file system
							// could refreshLocal on parent at this point
							newFile.create(input, false, null);
						} else {
							newFile.create(input, false, null);
						}
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static void createFileAndWriteContentInCorrespondFolder(
			String folderName, String fileName, String str) {
		InputStream input = new ByteArrayInputStream(str.toString().getBytes());
		String[] folders = folderName.split("\\.");
		IProject project = getCurrentProject();
		if (folders.length == 2) {
			IFolder systemFolder = project.getFolder(folders[0]);
			if (systemFolder.exists()) {
				IFolder siFolder = systemFolder.getFolder(folders[1]);
				if (siFolder.exists()) {
					IFile newFile = siFolder.getFile(fileName);
					try {
						File systemFile = null;
						if (newFile.exists())
							newFile.setContents(input, true, false, null);
						else
							systemFile = newFile.getLocation().toFile();

						if (systemFile.exists()) {
							// skip create -- in file system
							// could refreshLocal on parent at this point
							newFile.create(input, false, null);
						} else {
							newFile.create(input, false, null);
						}
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static void appendContentInFile(InputStream input) {
	}

	public static void createFolderAndFileInProject(IProject project,
			String folderName, String fileName, InputStream input) {
		IFolder newFolder = project.getFolder(folderName);
		if (!newFolder.exists()) {
			try {
				newFolder.create(true, true, null);
			} catch (CoreException e) {
				// ... ...
				// Deal with Operation
			}
		}

		createFileInFolder(newFolder, fileName, input);
	}

	public static void createFoldersInProject(String folderName) {
		IProject project = getCurrentProject();
		String[] folders = folderName.split("\\.");
		if (folders.length == 2) {
			IFolder systemFolder = project.getFolder(folders[0]);
			if (!systemFolder.exists()) {
				try {
					systemFolder.create(true, true, null);
				} catch (CoreException e) {
					// ... ...
					// Deal with Operation
				}
			}
			IFolder siFolder = systemFolder.getFolder(folders[1]);
			if (!siFolder.exists()) {
				try {
					siFolder.create(true, true, null);
				} catch (CoreException e) {
					// ... ...
					// Deal with Operation
				}
			} else {
				try {
					siFolder.delete(true, null);
					siFolder.create(true, true, null);
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}

		} else if (folders.length == 1) {

		} else {

		}
	}

	public static void createFolderAndFileInProject(String folderName,
			String fileName, InputStream input) {
		IProject project = getCurrentProject();
		String[] folders = folderName.split("\\.");
		if (folders.length == 2) {
			IFolder systemFolder = project.getFolder(folders[0]);
			if (!systemFolder.exists()) {
				try {
					systemFolder.create(true, true, null);
				} catch (CoreException e) {
					// ... ...
					// Deal with Operation
				}
			}
			IFolder siFolder = systemFolder.getFolder(folders[1]);
			if (!siFolder.exists()) {
				try {
					siFolder.create(true, true, null);
				} catch (CoreException e) {
					// ... ...
					// Deal with Operation
				}
			} else {
				try {
					siFolder.delete(true, null);
					siFolder.create(true, true, null);
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
			createFileInFolder(siFolder, fileName, input);

		} else if (folders.length == 1) {

		} else {

		}
	}


	
/* xjm */
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
		// TODO Auto-generated method stub
//		for(ComponentImplementation sysi : ((SystemInstanceImpl)_si).getInstantiatedObjects()){
//			if(sysi instanceof SystemImplementation){
//				Generate.generate((SystemImplementation)sysi);
//			}
//		}
//		SystemImplementation systemImplementation = getSystemImplementation2((SystemInstanceImpl)_si);
//		PublicPackageSection packageSection = (PublicPackageSection) systemImplementation.eContainer();
//		List<Classifier> classifiers2 = packageSection.getOwnedClassifiers();
//		
//		for(Classifier classifier : classifiers2) {
//			System.out.println(classifier.eClass().getName());
//			System.out.println(classifier.getName());
//		}
//		
		GenerateAda.generate(getSystemImplementation2((SystemInstanceImpl)_si));
		try {
			getCurrentProject().refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 *  
	 *  */
	public void postCprjCreate(IProject cPrj) {
		// TODO Auto-generated method stub
		IWorkspace iw=ResourcesPlugin.getWorkspace();
		IProject project=getCurrentProject();
		String foldername="codegen";
		IFolder folder=project.getFolder(foldername);
		try {
			folder.refreshLocal(IResource.DEPTH_ZERO, null);
		} catch (CoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(folder.exists()){
			try {
				
				for(IResource resource :cPrj.members()){
					if(resource.getName().equalsIgnoreCase("main.c")){
						resource.delete(true, null);
					}
				}
				//iw.copy(folder.members(), cPrj.getFullPath(), true, null);
				
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			if(folder.exists()){
				iw.copy(folder.members(), cPrj.getFullPath(), true, null);
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static IProject getGenProject() throws CoreException{
//		IWorkspaceRoot myWorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot();  
//		IProject[] projects = myWorkspaceRoot.getProjects();
//		for(IProject project : projects){
//			if(project instanceof CProject && project.getName().equalsIgnoreCase(projectname)){
//				if(project.getModificationStamp()!=0){
//					return project;
//				}
//			}
//			if(project.getName().equalsIgnoreCase(projectname)){
//				return project;
//			}
//			if(project.getModificationStamp()!=0){
//				return null;
//			}	
//		}
		return null;
	}
	
	

}
