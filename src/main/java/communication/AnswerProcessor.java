package communication;
import org.drools.runtime.StatefulKnowledgeSession;

public class AnswerProcessor {

	static StatefulKnowledgeSession session_;
	
	public static void SetSession(StatefulKnowledgeSession ksession) {
		session_ = ksession;
	}
	
	public static void Insert(Attribute attribute) {
		session_.insert(attribute);
	}
}
