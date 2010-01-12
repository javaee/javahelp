#
# @(#)Makefile	1.15 06/10/30
# 
# Copyright (c) 2006 Sun Microsystems, Inc.  All Rights Reserved.
# 
# Redistribution and use in source and binary forms, with or without
# modification, are permitted provided that the following conditions
# are met:
# 
# - Redistribution of source code must retain the above copyright
#   notice, this list of conditions and the following disclaimer.
# 
# - Redistribution in binary form must reproduce the above copyright
#   notice, this list of conditions and the following disclaimer in
#   the documentation and/or other materials provided with the
#   distribution.
# 
# Neither the name of Sun Microsystems, Inc. or the names of
# contributors may be used to endorse or promote products derived
# from this software without specific prior written permission.
# 
# This software is provided "AS IS," without a warranty of any
# kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
# WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
# EXCLUDED. SUN MICROSYSTEMS, INC. ("SUN") AND ITS LICENSORS SHALL
# NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF
# USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
# DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR
# ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL,
# CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND
# REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF OR
# INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
# POSSIBILITY OF SUCH DAMAGES.
# 
# You acknowledge that this software is not designed, licensed or
# intended for use in the design, construction, operation or
# maintenance of any nuclear facility. 
#

# Makefile for use with Microsoft's nmake; it assumes your Java bin directory
# is in your path, and that a CLASSPATH environment variable is defined.
#
# @(#)Makefile 1.15 10/30/06

# idehelp.jar is the JAR that contains all the help information
IDEHELP_JAR=..\..\hsjar\idehelp.jar

# Should be conditional...
JHHOME=..\..\..\javahelp

INDEXER=$(JHHOME)\bin\jhindexer
JAR=jar

IDEHELP_SOURCE_FILES = \
	IdeHelp.hs \
	$(IDEHELP_META_FILES) \
	$(IDEHELP_CONTENT_FILES) \
	$(IDEHELP_OTHER_FILES)

IDEHELP_META_FILES = \
	Map.jhm \
	IdeHelpTOC.xml \
	IdeHelpIndex.xml

IDEHELP_CONTENT_FILES = \
	idehelp\jde.html \
	idehelp\build\build.html \
	idehelp\build\compilefile.html \
	idehelp\build\javac.html \
	idehelp\build\jwsbuild.html \
	idehelp\build\compiler_pref.html \
	idehelp\pce\dialogs\actions.html \
	idehelp\pce\dialogs\find.html \
	idehelp\pce\dialogs\keyboard.html \
	idehelp\pce\dialogs\preferences.html \
	idehelp\pce\dialogs\replace.html \
	idehelp\pce\basic.html \
	idehelp\pce\controls.html \
	idehelp\pce\edit_source.html \
	idehelp\pce\font.html \
	idehelp\pce\syntax.html \
	idehelp\pce\tabs.html \
	idehelp\pce\version.html \
	idehelp\start\breakpoint.html \
	idehelp\start\browse.html \
	idehelp\start\build.html \
	idehelp\start\copyproject.html \
	idehelp\start\create.html \
	idehelp\start\customize.html \
	idehelp\start\debug.html \
	idehelp\start\edit.html \
	idehelp\start\editbasics.html \
	idehelp\start\editbuild.html \
	idehelp\start\editcode.html \
	idehelp\start\editlesson.html \
	idehelp\start\enddebug.html \
	idehelp\start\errors.html \
	idehelp\start\evaluate.html \
	idehelp\start\fixbuild.html \
	idehelp\start\flow.html \
	idehelp\start\html.html \
	idehelp\start\import.html \
	idehelp\start\hierarchy.html \
	idehelp\start\method.html \
	idehelp\start\overview.html \
	idehelp\start\portfolio.html \
	idehelp\start\profile.html \
	idehelp\start\project.html \
	idehelp\start\quickstart.html \
	idehelp\start\runpage.html \
	idehelp\start\run.html \
	idehelp\start\simpledebug.html \
	idehelp\start\startdebug.html \
	idehelp\start\starttoc.html \
	idehelp\start\step.html \
	idehelp\start\strings.html \
	idehelp\start\terminology.html \
	idehelp\start\threads.html \
	idehelp\start\visual\build.html \
	idehelp\start\visual\button.html \
	idehelp\start\visual\design.html \
	idehelp\start\visual\grid.html \
	idehelp\start\visual\label.html \
	idehelp\start\visual\names.html \
	idehelp\start\visual\project.html \
	idehelp\start\visual\visual.html \
	idehelp\start\visual\action.html \
	idehelp\start\tools.html \
	idehelp\start\clock\AddCode.html \
	idehelp\start\clock\BeanLayout.html \
	idehelp\start\clock\BeanOps.html \
	idehelp\start\clock\BeanStart.html \
	idehelp\start\clock\BuildBean.html \
	idehelp\start\clock\ChangeClock.html \
	idehelp\start\clock\BeansOverview.html \
	idehelp\start\clock\CreateBean.html \
	idehelp\start\clock\EditJar.html \
	idehelp\start\clock\ImportBeans.html \
	idehelp\start\clock\Standalone.html \
	idehelp\start\clock\UpdateClock.html \
	idehelp\debug\Breakpoint.html \
	idehelp\debug\Conditional.html \
	idehelp\debug\Counted.html \
	idehelp\debug\Arguments.html \
	idehelp\debug\AddException.html \
	idehelp\debug\BreakpointOverview.html \
	idehelp\debug\Editor.html \
	idehelp\debug\EndDebug.html \
	idehelp\debug\Eval.html \
	idehelp\debug\DebugTOC.html \
	idehelp\debug\Exceptions.html \
	idehelp\debug\Messages.html \
	idehelp\debug\Overview.html \
	idehelp\debug\RemoteDebug.html \
	idehelp\debug\Resume.html \
	idehelp\debug\Run.html \
	idehelp\debug\RemoveBreakpoint.html \
	idehelp\debug\Step.html \
	idehelp\debug\Stack.html \
	idehelp\debug\Source.html \
	idehelp\debug\StartOver.html \
	idehelp\debug\StepOver.html \
	idehelp\debug\Start.html \
	idehelp\debug\Suspend.html \
	idehelp\debug\Threads.html \
	idehelp\debug\StopAtBreakpoint.html \
	idehelp\debug\StopInBreakpoint.html \
	idehelp\debug\Variables.html \
	idehelp\debug\ViewThreads.html \
	idehelp\sbrowse\javadoc.html \
	idehelp\sbrowse\sbrowse.html \
	idehelp\sbrowse\class.html \
	idehelp\sbrowse\strings.html \
	idehelp\manager\AppletClass.html \
	idehelp\manager\AppletWizard.html \
	idehelp\manager\BeansProject.html \
	idehelp\manager\BuildDisplay.html \
	idehelp\manager\BeanNameWizard.html \
	idehelp\manager\CopyApplet.html \
	idehelp\manager\CreateApplet.html \
	idehelp\manager\ChangePortfolio.html \
	idehelp\manager\ChangeProject.html \
	idehelp\manager\DebugDisplay.html \
	idehelp\manager\EditHtml.html \
	idehelp\manager\CreatePackage.html \
	idehelp\manager\CreatePortfolio.html \
	idehelp\manager\CreateStandalone.html \
	idehelp\manager\EditProject.html \
	idehelp\manager\GenerateHtml.html \
	idehelp\manager\Import.html \
	idehelp\manager\CreatePortfolio2.html \
	idehelp\manager\ExistingBeans.html \
	idehelp\manager\ExistingSources.html \
	idehelp\manager\ImportWizard.html \
	idehelp\manager\MainClass.html \
	idehelp\manager\GeneralDisplay.html \
	idehelp\manager\ImportDirectory.html \
	idehelp\manager\ImportPortfolio.html \
	idehelp\manager\ImportProject2.html \
	idehelp\manager\ManagerTOC.html \
	idehelp\manager\NewJavaBean.html \
	idehelp\manager\PackageWizard.html \
	idehelp\manager\NestedProjects.html \
	idehelp\manager\Paths.html \
	idehelp\manager\Portfolio.html \
	idehelp\manager\ProjectManager.html \
	idehelp\manager\Projects.html \
	idehelp\manager\RemovePortfolio.html \
	idehelp\manager\Run.html \
	idehelp\manager\RunDisplay.html \
	idehelp\manager\ProjectWizard.html \
	idehelp\manager\indexcontent.html \
	idehelp\manager\packages.html \
	idehelp\manager\RemoveProject.html \
	idehelp\manager\RunStandalone.html \
	idehelp\menus\browse.html \
	idehelp\menus\build.html \
	idehelp\menus\debug.html \
	idehelp\menus\edit.html \
	idehelp\menus\file.html \
	idehelp\menus\help.html \
	idehelp\menus\preferences.html \
	idehelp\menus\gui.html \
	idehelp\menus\project.html \
	idehelp\menus\windows.html \
	idehelp\profiler\menus.html \
	idehelp\profiler\dialogs\custom.html \
	idehelp\profiler\profile.html \
	idehelp\toolbars\pce_debug.html \
	idehelp\toolbars\main.html \
	idehelp\visual\add_button.html \
	idehelp\visual\add_canvas.html \
	idehelp\visual\add_images.html \
	idehelp\visual\add_labels.html \
	idehelp\visual\add_choicemenu.html \
	idehelp\visual\addcallbacks.html \
	idehelp\visual\beantips.html \
	idehelp\visual\add_listboxes.html \
	idehelp\visual\add_scrollbar.html \
	idehelp\visual\add_textfields.html \
	idehelp\visual\chg_colors.html \
	idehelp\visual\chg_fonts.html \
	idehelp\visual\chg_palette.html \
	idehelp\visual\chg_container.html \
	idehelp\visual\class_usage.html \
	idehelp\visual\custom.html \
	idehelp\visual\dboxes.html \
	idehelp\visual\editbean.html \
	idehelp\visual\dialogs\action.html \
	idehelp\visual\dialogs\attributes.html \
	idehelp\visual\dialogs\operations.html \
	idehelp\visual\dialogs\filter1.html \
	idehelp\visual\generate.html \
	idehelp\visual\groups_basic.html \
	idehelp\visual\gui_examples.html \
	idehelp\visual\groupShadow-background.html \
	idehelp\visual\groups_advanced.html \
	idehelp\visual\importing.html \
	idehelp\visual\jbeanstory.html \
	idehelp\visual\labelshadow.html \
	idehelp\visual\menuops.html \
	idehelp\visual\menus.html \
	idehelp\visual\modify.html \
	idehelp\visual\layout.html \
	idehelp\visual\nest_panels.html \
	idehelp\visual\newwindow.html \
	idehelp\visual\name_conflict.html \
	idehelp\visual\ops.html \
	idehelp\visual\palette.html \
	idehelp\visual\resize.html \
	idehelp\visual\radio_panel.html \
	idehelp\visual\position_comps.html \
	idehelp\visual\running.html \
	idehelp\visual\upgrade.html \
	idehelp\visual\visual.html \
	idehelp\visual\tab_folder.html \
	idehelp\visual\wdw-resize.html \
	idehelp\visual\examples\columnlist.html \
	idehelp\visual\examples\rolo1.html \
	idehelp\visual\examples\rolo2.html \
	idehelp\visual\examples\rolo3.html \
	idehelp\visual\examples\rolo3b.html \
	idehelp\misc\jws_options.html \
	idehelp\misc\tcp.html \
	idehelp\misc\console_input.html

IDEHELP_OTHER_FILES = \
	images\toplevel.gif \
	images\spacer2_t.gif \
	images\hg_see.gif \
	images\hg_proc.gif \
	images\hg_exam.gif \
	images\label.gif \
	images\button.gif \
	images\labelbar.gif \
	images\calculator.gif \
	images\browser.gif \
	images\bullet_ball_gold_t.gif \
	images\hg_tip.gif \
	images\hg_note.gif \
	images\gettingstarted_header3_t.gif \
	images\title.gif \
	images\flow.gif \
	images\portfolio.gif \
	images\spacer_4pix.gif \
	images\menu.gif \
	images\toolbar.gif \
	images\url.gif \
	images\footer_button_spacer_t.gif \
	images\gettingstarted_header3.gif \
	images\javascribe_dummy.gif \
	images\helpviewer.gif \
	images\nb_spacer_t.gif \
	images\stages.gif \
	images\NameProj.gif \
	images\page_glyphs_line_break_11.gif \
	images\page_glyphs_nonbreaking_11.gif \
	images\page_glyphs_target_11.gif \
	images\applet-failed.gif \
	images\image-failed.gif \
	images\jwsstartup\button.active.gif \
	images\jwsstartup\button.down.gif \
	images\jwsstartup\button.up.gif \
	images\tProfile.gif \
	images\profiler\profiler_small.gif \
	images\visual\cell.gif \
	images\visual\file_menu.gif \
	images\visual\layout.gif \
	images\visual\menu_editor.gif \
	images\visual\palette_small.gif \
	images\visual\menu_editor_cropped.gif \
	images\visual\handles.gif \
	images\visual\insets.gif \
	images\visual\resize.gif \
	images\visual\selected.gif \
	images\visual\fill.gif \
	images\visual\row_column.gif \
	images\visual\cellfill1.gif \
	images\visual\cellfill2.gif \
	images\visual\extend1.gif \
	images\visual\extend2.gif \
	images\visual\resize1.gif \
	images\visual\resize2.gif \
	images\visual\row-col.gif \
	images\visual\selection-handles.gif \
	images\visual\buttons\border.gif \
	images\visual\buttons\button.gif \
	images\visual\buttons\checkbox.gif \
	images\visual\buttons\choice.gif \
	images\visual\buttons\dblist.gif \
	images\visual\buttons\flow.gif \
	images\visual\buttons\grid_panel.gif \
	images\visual\buttons\image_button.gif \
	images\visual\buttons\image_label.gif \
	images\visual\buttons\labelbar.gif \
	images\visual\buttons\listbox.gif \
	images\visual\buttons\radio.gif \
	images\visual\buttons\text.gif \
	images\visual\buttons\text_field.gif \
	images\visual\buttons\selection.gif \
	images\visual\buttons\text_label.gif \
	images\visual\buttons\vscroll.gif \
	images\visual\buttons\card.gif \
	images\visual\buttons\tabfolder.gif \
	images\visual\buttons\custom.gif \
	images\visual\buttons\smallgrid.gif \
	images\visual\tcell_chg1.gif \
	images\visual\tcell_chg2.gif \
	images\visual\tcellfill1.gif \
	images\visual\tcellfill2.gif \
	images\visual\textend1.gif \
	images\visual\textend2.gif \
	images\visual\tresize1.gif \
	images\visual\tresize2.gif \
	images\visual\trow-col.gif \
	images\visual\tselection-handles.gif \
	images\visual\textend1b.gif \
	images\visual\cell_chg2.gif \
	images\visual\cell_chg1.gif \
	images\visual\extend1b.gif \
	images\visual\extend3.gif \
	images\visual\textend3.gif \
	images\visual\examples\grid_panel.gif \
	images\visual\examples\cardfile.gif \
	images\visual\examples\emailwin.gif \
	images\visual\examples\grid_panel1.gif \
	images\vj\calendar_UI.gif \
	images\vj\calendar_comp.gif \
	images\vj\calendar_example.gif \
	images\vj\calendar_gui.gif \
	idehelp\start\visual\calculator2.gif \
	idehelp\start\visual\calculatorpreview.gif \
	idehelp\start\visual\calculator1.gif \
	idehelp\start\visual\calculator3.gif \
	idehelp\start\clock\clock-layout.gif \
	idehelp\start\clock\deskclock.gif \
	idehelp\start\clock\beanstab.gif \
	idehelp\start\clock\updateclock.gif \
	idehelp\images\rule.gif \
	idehelp\images\calendar.gif \
	idehelp\images\db-eval_u.gif \
	idehelp\images\db-restart_u.gif \
	idehelp\images\db-down-frame_u.gif \
	idehelp\images\db-resume_u.gif \
	idehelp\images\db-stop-at_u.gif \
	idehelp\images\db-up-frame_u.gif \
	idehelp\images\db-run-to-here_u.gif \
	idehelp\images\db-step-into_u.gif \
	idehelp\images\db-step-over_u.gif \
	idehelp\images\file-open_u.gif \
	idehelp\images\file-print_u.gif \
	idehelp\images\file-save_u.gif \
	idehelp\images\find_u.gif \
	idehelp\images\nb_01_u.gif \
	idehelp\images\nb_02_u.gif \
	idehelp\images\nb_03_u.gif \
	idehelp\images\nb_04_u.gif \
	idehelp\images\nb_05_u.gif \
	idehelp\images\pm_01_u.gif \
	idehelp\images\pm_02_u.gif \
	idehelp\images\pm_03_u.gif \
	idehelp\images\pm_04_u.gif \
	idehelp\images\pm_06_u.gif \
	idehelp\images\tb-build_u.gif \
	idehelp\images\pm_05_u.gif \
	idehelp\images\tb-profile_u.gif \
	idehelp\images\tb-run_u.gif \
	idehelp\images\tb-help_u.gif \
	idehelp\images\tb-html-edit_u.gif \
	idehelp\images\tb-project-manager_u.gif \
	idehelp\images\tb-string-search_u.gif \
	idehelp\images\tb_03_u.gif \
	idehelp\images\tb_07_u.gif \
	idehelp\images\tb_09_u.gif \
	idehelp\images\tb_10_u.gif \
	idehelp\images\tb_11_u.gif \
	idehelp\images\tutorial_u.gif

IDEHELP_SEARCH_FILES = \
	JavaHelpIndex\DOCS \
	JavaHelpIndex\DOCS.TAB \
	JavaHelpIndex\OFFSETS \
	JavaHelpIndex\POSITIONS \
	JavaHelpIndex\SCHEMA \
	JavaHelpIndex\TMAP

all: $(IDEHELP_JAR)

searchfiles: $(IDEHELP_SEARCH_FILES)

clean:
	del $(IDEHELP_JAR)
	del $(IDEHELP_SEARCH_FILES)

$(IDEHELP_JAR): $(IDEHELP_SOURCE_FILES) $(IDEHELP_SEARCH_FILES) ..\..\hsjar
	@del /f/q $(IDEHELP_JAR)
	$(JAR) -cvf $@ $(IDEHELP_SOURCE_FILES) $(IDEHELP_SEARCH_FILES)

$(IDEHELP_SEARCH_FILES): $(IDEHELP_CONTENT_FILES)
	@del /f/q $(IDEHELP_SEARCH_FILES)
	@mkdir JavaHelpIndex
	$(INDEXER) -verbose $(IDEHELP_CONTENT_FILES)

..\..\hsjar:
	mkdir ..\..\hsjar
