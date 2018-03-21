package cn.edu.nuaa.aadl2.generator.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.internal.Workbench;
import org.osate.workspace.IAadlElement;
import org.osate.workspace.IAadlProject;

import cn.edu.nuaa.aadl2.generator.template.SubprogramTemplate;
import cn.edu.nuaa.aadl2.generator.templateAda.TemplateAda;

@SuppressWarnings("restriction")
public class Tools {

	/* ϵͳʵ��Ŀ¼ */
	/* system folder create for system instance */
	public static IFolder ifolder = null;

	/* create system folder for system instance */
	public static void folder(String folderName) {
		IProject project = getCurrentProject();
		IFolder systemFolder = project.getFolder(folderName);
		if (!systemFolder.exists()) {
			try {
				systemFolder.create(true, true, null);
				ifolder = systemFolder;
			} catch (CoreException e) {
				// ... ...
				// Deal with Operation
			}
		} else {
			try {
				systemFolder.delete(true, null);
				systemFolder.create(true, true, null);
				ifolder = systemFolder;
			} 
			catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}

	public static IFolder getfolder(String folder) {
		IProject project = getCurrentProject();
		IFolder systemFolder = project.getFolder(folder);
		if (!systemFolder.exists()) {
			try {
				systemFolder.create(true, true, null);
				return systemFolder;
			} catch (CoreException e) {
				// ... ...
				// Deal with Operation
			}
		}
		return systemFolder;
			
	}
	
	public static void subFolder(String folder) {
		IProject project = getCurrentProject();
		if (ifolder.exists()) {
			IFolder infolder = project.getFolder(folder);
			if (infolder.exists()) {
				try {
					infolder.create(true, true, null);
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}
			} else {
//				try {
//					infolder.delete(true, null);
//					infolder.create(true, true, null);
//				} catch (CoreException e) {
//					// TODO Auto-generated catch block
//					// e.printStackTrace();
//				}
			}
		}
	}

	public static void createFile(IFolder folder, String fileName, String in) {
		InputStream input = new ByteArrayInputStream(in.getBytes());
		IFile newFile = folder.getFile(fileName);
		try {
			if (newFile.exists()) {
				newFile.setContents(input, true, false, null);
			} else {
				newFile.create(input, false, null);
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	public static void createFile(String folder, String fileName, String in) {
		IProject project = getCurrentProject();
		IFolder systemFolder = project.getFolder(folder);
		InputStream input = new ByteArrayInputStream(in.getBytes());
		IFile newFile = systemFolder.getFile(fileName);
		try {
			if (newFile.exists()) {
				newFile.setContents(input, true, false, null);
			} else {
				newFile.create(input, false, null);
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	public static void createFile(String fileName, String in) {
		InputStream input = new ByteArrayInputStream(in.getBytes());
		if (ifolder != null) {
			IFile newFile = ifolder.getFile(fileName);
			try {
				if (newFile.exists()) {
//					newFile.delete(true, null);
//					newFile.create(input, false, null);
//					newFile.setContents(input, true, false, null);
				} else {
					newFile.create(input, false, null);
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("ifolder is null");
		}
	}

	public static void creatSubprogramFile(String fileName, String initline, String in) {
		InputStream inputStream = new ByteArrayInputStream(in.getBytes());
		if(ifolder != null) {
			IFile newFile = ifolder.getFile(fileName);
			try {
				if(!newFile.exists()) {
					newFile.create(new ByteArrayInputStream(initline.getBytes()), false, null);
				}
				newFile.appendContents(inputStream , 1, null);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	public static void dealSubprogramFile(String fileName) {
		String packageName = "end " + TemplateAda.packageName+"_Subprograms;";
		InputStream inputStream = new ByteArrayInputStream(packageName.getBytes());
		if(ifolder != null) {
			IFile newFile = ifolder.getFile(fileName);
			try {
				if(newFile.exists()) {
					newFile.appendContents(inputStream , 1, null);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	public static void addContent(String fileName, String in) {
		in=in+"\n";
		InputStream input = new ByteArrayInputStream(in.getBytes());
		if (ifolder != null) {
			IFile newFile = ifolder.getFile(fileName);
			try {
				if (newFile.exists()) {
					newFile.appendContents(input, 1, null);
					newFile.refreshLocal(IResource.DEPTH_ZERO, null);
				} else {
					newFile.create(input, false, null);
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("ifolder is null");
		}
	}
	
	public static void sub_addContent(String folder,String fileName,String in) {
		in=in+"\n";
		InputStream input = new ByteArrayInputStream(in.getBytes());
		IFolder meta_folder=getfolder(folder);
		if (ifolder != null) {
			IFile newFile = meta_folder.getFile(fileName);
			try {
				if (newFile.exists()) {
					newFile.appendContents(input, 1, null);
					newFile.refreshLocal(IResource.DEPTH_ZERO, null);
				} else {
					newFile.create(input, false, null);
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("ifolder is null");
		}
	}

	public static IProject getCurrentProject() {
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

	public static IProject getProject() {

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
	
	public static String getPackageName(String input) {
		Pattern pattern = Pattern.compile("(\\(name: (.*)\\))");
		Matcher matcher = pattern.matcher(input);
		if(matcher.find()) {
			return matcher.group(2);
		}
		return "";
	}
	
	public static String getCalledSubprogramName(String input) {
		Pattern pattern = Pattern.compile("(\\(name: (.*?)\\))");
		Matcher matcher = pattern.matcher(input);
		if(matcher.find()) {
			System.out.println(matcher.group(0));
			System.out.println(matcher.group(1));
			System.out.println(matcher.group(2));
			return matcher.group(2);
		}
		return "";
	}

}
