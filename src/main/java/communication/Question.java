package communication;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import org.yaml.snakeyaml.*;



public class Question {

	private String id_;
	private String fact_name_;
	private String type_;
	private String text_;
	private HashMap<String, String> answers_;
	
	public Question(String id_, String fact_name_, String type_, String text_,
			HashMap<String, String> answers_) {
		super();
		this.id_ = id_;
		this.fact_name_ = fact_name_;
		this.type_ = type_;
		this.text_ = text_;
		this.answers_ = answers_;
	}

	public String getText() {
		return text_;
	}

	public void setText(String text_) {
		this.text_ = text_;
	}

	public HashMap<String, String> getAnswers() {
		return answers_;
	}

	public void setAnswers(HashMap<String, String> answers_) {
		this.answers_ = answers_;
	}
	
	 public String getFact_name_() {
		return fact_name_;
	}

	public String getType_() {
		return type_;
	}
	
	private void Ask() {
		ArrayList<String> answers = new ArrayList<String>();
		ArrayList<String> labels = new ArrayList<String>();
		
		for (String key : answers_.keySet()) {
			answers.add(answers_.get(key));
			labels.add(key);
		}

		int[] selected;
		if (type_.equals("multiple"))
			selected = MultipleAnswerPrompt(labels, answers);
		else
			selected = SingleAnswerPrompt(labels, answers);
		
		AddAttributes(labels, selected);
	}
	
	private void AddAttributes(ArrayList<String> labels) {
		for (String label : labels) {
			if (label.equals("irrelevant")) continue;
			Attribute attribute = Attribute.Parse(fact_name_, label);
			AnswerProcessor.Insert(attribute);
		}
	}
	
	private void AddAttributes(ArrayList<String> labels, int[] selected) {
		for (int index : selected)
			if (labels.get(index).equals("irrelevant")) {
				AddAttributes(labels);
				return;
			}

		for (int index : selected) {
			Attribute attribute = Attribute.Parse(fact_name_, labels.get(index));
			AnswerProcessor.Insert(attribute);
		}
	}

	private int[] SingleAnswerPrompt(ArrayList<String> labels, ArrayList<String> answers) {
	    JList list = new JList(answers.toArray());
	    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
	    
	    Object[] options = {text_, " ", list};
	    while (list.getSelectedIndices().length < 1) {
	    	JOptionPane.showMessageDialog(null, options, "Select single answer", JOptionPane.PLAIN_MESSAGE);
	    	options = new Object[] {text_, " ", "Error: Select atleast one answer!", " ", list };
	    }
	    return list.getSelectedIndices();
	}
	
	private int[] MultipleAnswerPrompt(ArrayList<String> labels, ArrayList<String> answers) {
	    JList list = new JList(answers.toArray());
	    
	    Object[] options = {text_, " ", list};
	    while (list.getSelectedIndices().length < 1) {
	    	JOptionPane.showMessageDialog(null, options, "Select multiple answers", JOptionPane.PLAIN_MESSAGE);
	    	options = new Object[] {text_, " ", "Error: Select atleast one answer!", " ", list };
	    }
	    return list.getSelectedIndices();
	}

	public static void Ask(String id) {
	    Question q = Question.Get(id);
	    q.Ask();
	  }
	
	public static void Notify(String message) {
    	    JOptionPane.showMessageDialog(null, message, "Select multiple answers", JOptionPane.PLAIN_MESSAGE);
	}
	
	private static HashMap<String, Question> questions = new HashMap<String, Question>();

	  public static Question Get(String id) {
	    if (Question.questions.isEmpty()) {
	        Yaml yaml = new Yaml();
	        Map<String, Map<String, Object>> map;
	        try {
	          map = (Map<String, Map<String, Object>>)yaml.load((InputStream)(new FileInputStream(new File("yaml/questions.yaml"))));

	          
	          for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
	            Question q = new Question(entry.getKey(),
	            		                  (String)(entry.getValue().get("fact_name")),
	            		                  (String)(entry.getValue().get("type")),
	            		                  (String)(entry.getValue().get("text")),
	            		                  (HashMap<String, String>)(entry.getValue().get("answers")));
	            Question.questions.put(entry.getKey(), q);
	          }
	        } catch (FileNotFoundException e) {
	          e.printStackTrace();
	        }
	      }
		  return questions.get(id);
	  }
	
}
