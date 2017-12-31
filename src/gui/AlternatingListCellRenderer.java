/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author ferrinsp
 */
public class AlternatingListCellRenderer extends JLabel implements ListCellRenderer {
    private final Color even, odd, selected;
   
   public AlternatingListCellRenderer(){
      this(new Color(209,220,224), new Color(180,212,222), Color.BLUE);
   }
   public AlternatingListCellRenderer(Color even, Color odd, Color selected){
      setOpaque(true);
      this.even = even;
      this.odd = odd;
      this.selected = selected;
   }
   @Override
   public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        setText(value.toString());
        
        if (index % 2 == 0) setBackground(even);
        else setBackground(odd);

        if (isSelected){
            setBackground(selected);
            setForeground(Color.WHITE);
        }else{
            setForeground(list.getForeground());
        }
        return this;
   }
}
