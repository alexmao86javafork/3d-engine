package mino.ermal.engine3d;

import mino.ermal.engine3d.matrix.RotationMatrix3D;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.*;
import java.awt.*;
import java.util.Iterator;

/**
 * User: mino.ermal(AT)gmail.com
 * Date: Jun 15, 2008
 * Time: 2:15:11 PM
 *
 * This is the user interface panel where controls are put to interact with the user
 * and with the Engine3DRenderPanel
 *
 */
public class Engine3DControlPanel extends JSplitPane implements ItemListener, MouseMotionListener, MouseListener{
    private Engine3DRenderPanel engine3dRenderPanel;
    private JPanel controlPanel;

    //renderer options panel
    private JPanel rendererOptionPanel;
    private JCheckBox enableFilled;
    private JCheckBox enableWireframed;
    private JCheckBox enableCoords;

    //object selection panel
    private JComboBox selection;

    private Point startPoint; //used to save the previous point when the mouse is draged
    private Point endPoint; //used to save the last point when the mouse is draged

    /**
     * Class constructor, initializes the user interface components
     * and adds the main content pane to itself
     */
    public Engine3DControlPanel() {
        initUserInterface();
    }

    /**
     * All user interface component initialization and configuration
     * is done here.
     */
    private void initUserInterface(){
        engine3dRenderPanel = new Engine3DRenderPanel();
        engine3dRenderPanel.addMouseListener(this);
        engine3dRenderPanel.addMouseMotionListener(this);

        //renderer options panel
        enableFilled = new JCheckBox("Filled", engine3dRenderPanel.getFilled());
        enableFilled.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                engine3dRenderPanel.setFilled(enableFilled.isSelected());
                engine3dRenderPanel.render();
            }
        });
        enableWireframed = new JCheckBox("Wireframed", engine3dRenderPanel.getWireframed());
        enableWireframed.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                engine3dRenderPanel.setWireframed(enableWireframed.isSelected());
                engine3dRenderPanel.render();
            }
        });
        enableCoords = new JCheckBox("Show Coordinate Axes", engine3dRenderPanel.getRenderAxis());
        enableCoords.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                engine3dRenderPanel.setRenderAxis(enableCoords.isSelected());
                engine3dRenderPanel.render();
            }
        });
        rendererOptionPanel = new JPanel(new GridLayout(3, 1));
        rendererOptionPanel.add(enableFilled);
        rendererOptionPanel.add(enableWireframed);
        rendererOptionPanel.add(enableCoords);


        //object selection panel
        selection = new JComboBox();
        selection.setSize(100, 50);
        Iterator iterator = engine3dRenderPanel.getShapeList();
        while (iterator.hasNext()) {
            selection.addItem(iterator.next());
        }
        selection.addItemListener(this);

        //control panel
        controlPanel = new JPanel();
        controlPanel.setBackground(Color.lightGray);
        controlPanel.add(selection);
        controlPanel.add(rendererOptionPanel);
        
        //main content split pane
        setDividerSize(3);
        setResizeWeight(1.0);
        setAutoscrolls(false);
        setContinuousLayout(true);
        setLastDividerLocation(-1);
        setRequestFocusEnabled(true);
        //add renderer panel on the left
        setLeftComponent(engine3dRenderPanel);
        //add the control panel on the right
        setRightComponent(controlPanel);

    }



    public void itemStateChanged(ItemEvent itemEvent) {
        Object o = itemEvent.getItem();
        engine3dRenderPanel.select((String) o);
    }

    private void rotateFromDrag(){
        double angleMultiplyFactor=0.02;
        double angleY=angleMultiplyFactor*(endPoint.getX()-startPoint.getX());
        double angleX=angleMultiplyFactor*(endPoint.getY()-startPoint.getY());
        engine3dRenderPanel.rotate(new RotationMatrix3D(-angleX, angleY, 0));
    }
    private void scaleFromDrag(){
        double scaleMultiplyFactor=0.01;
        double scaleFactor=scaleMultiplyFactor*(endPoint.getY()-startPoint.getY());
        engine3dRenderPanel.scale(1+scaleFactor);
    }

    /**
     * Invoked when the mouse button is pressed
     * @param event mouse button pressed event
     */
    public void mousePressed(MouseEvent event) {
        startPoint=event.getPoint();
    }

    /**
     * Invoked when the mouse is draged
     * @param event mouse draged event
     */
    public void mouseDragged(MouseEvent event) {
        endPoint=event.getPoint();
        
        if((event.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK){
            rotateFromDrag();
        }else if((event.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK){
            scaleFromDrag();
        }

        startPoint=endPoint;
    }

    /*
    The following methods are implemented from the MouseMotionListener and
    MouseListener interfaces.
    All of them are empty because we ignore other mouse events except for the two
    methods above
     */


    public void mouseMoved(MouseEvent event) {
        //not needed
    }

    public void mouseClicked(MouseEvent event) {
        //not needed
    }

    public void mouseEntered(MouseEvent event) {
        //not needed
    }

    public void mouseExited(MouseEvent event) {
        //not needed
    }

    public void mouseReleased(MouseEvent event) {
        //not needed
    }
}
