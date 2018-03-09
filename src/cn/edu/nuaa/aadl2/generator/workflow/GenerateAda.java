package cn.edu.nuaa.aadl2.generator.workflow;

import cn.edu.nuaa.aadl2.generator.templateAda.ClassifierTemplateAda;
import cn.edu.nuaa.aadl2.generator.templateAda.SubcomponentTemplateAda;
import cn.edu.nuaa.aadl2.generator.templateAda.TemplateAda;
import cn.edu.nuaa.aadl2.generator.utils.StringUtils;
import cn.edu.nuaa.aadl2.generator.utils.Tools;
import com.google.common.base.Objects;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.osate.aadl2.Classifier;
import org.osate.aadl2.Subcomponent;
import org.osate.aadl2.SystemImplementation;
import org.osate.aadl2.impl.PublicPackageSectionImpl;

@SuppressWarnings("all")
public class GenerateAda {
  private final static String adaFolder = "Ada_codes";
  
  public static void generate(final SystemImplementation system) {
    String _name = system.getName();
    boolean _notEquals = (!Objects.equal(_name, null));
    if (_notEquals) {
      Tools.folder(GenerateAda.adaFolder);
      String _convert = StringUtils.convert(system.getName());
      String _plus = ((GenerateAda.adaFolder + "/") + _convert);
      Tools.folder(_plus);
      String _convert_1 = StringUtils.convert(system.getName());
      String _plus_1 = ((GenerateAda.adaFolder + "/") + _convert_1);
      TemplateAda.systemfolder = _plus_1;
      TemplateAda.packageName = Tools.getPackageName(system.eContainer().eContainer().toString());
      TemplateAda.subprogramsFileName = (TemplateAda.packageName + "_Subprograms");
    }
    EList<Subcomponent> _allSubcomponents = system.getAllSubcomponents();
    boolean _notEquals_1 = (!Objects.equal(_allSubcomponents, null));
    if (_notEquals_1) {
      EList<Subcomponent> _allSubcomponents_1 = system.getAllSubcomponents();
      for (final Subcomponent subcomponent : _allSubcomponents_1) {
        {
          SubcomponentTemplateAda.template(subcomponent);
          System.out.println(subcomponent.getName());
        }
      }
    }
    EObject _eContainer = system.eContainer();
    PublicPackageSectionImpl packageSectionImpl = ((PublicPackageSectionImpl) _eContainer);
    EList<Classifier> classifiers = packageSectionImpl.getOwnedClassifiers();
    for (final Classifier classifier : classifiers) {
      ClassifierTemplateAda.template(classifier);
    }
    String _lowerCase = TemplateAda.subprogramsFileName.toLowerCase();
    String _plus_2 = (_lowerCase + ".ads");
    Tools.dealSubprogramFile(_plus_2);
    String _lowerCase_1 = TemplateAda.subprogramsFileName.toLowerCase();
    String _plus_3 = (_lowerCase_1 + ".adb");
    Tools.dealSubprogramFile(_plus_3);
  }
}
