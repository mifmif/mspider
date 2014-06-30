package com.mifmif.networking.mspider;

import java.io.IOException;

import com.mifmif.networking.mspider.database.dao.api.WebsiteDao;
import com.mifmif.networking.mspider.database.dao.impl.JpaDaoFactory;
import com.mifmif.networking.mspider.model.Field;
import com.mifmif.networking.mspider.model.URLParameter;
import com.mifmif.networking.mspider.model.URLParameterType;
import com.mifmif.networking.mspider.model.URLPattern;
import com.mifmif.networking.mspider.model.Website;

/**
 * @author y.mifrah
 *
 */
public class Main {
	WebsiteDao websiteDao = JpaDaoFactory.getJpaWebsiteDao();

	public static void main(String[] args) throws IOException {
		System.setProperty("java.net.useSystemProxies", "true");
		Website website = new Main().bootstrap();
		Engine engine = new Engine(website);
		engine.start();
	}

	/**
	 * Bootstrap Database and store a sample in the DB for testing purpose :
	 * website with some urlPattern and fields and relation between them.
	 */
	Website bootstrap() {
		String host = "http://stackoverflow.com";

		Website website = new Website(host);
		// Create urls pattern
		URLPattern questionsHome = new URLPattern(website, "/questions", "HomePage");
		URLPattern nextQuestionsPage = new URLPattern(website, "/questions\\?page=[0-9]+&sort=newest", "NextPage");
		URLPattern questionPage = new URLPattern(website, "/questions/[0-9]+/.+", "QuestionPage");
		URLPattern excludedPat1 = URLPattern.builder().withUrlPattern("/questions\\?page=[0-9]{3,}&sort=newest").withExcludeFrom(website).build();
		// questionsHome mapping links to others pattern pages
		questionsHome.getNextUrls().add(questionPage);
		questionsHome.getNextUrls().add(nextQuestionsPage);
		// nextQuestionsPage mapping links to others pattern pages
		nextQuestionsPage.getNextUrls().add(questionPage);
		nextQuestionsPage.getNextUrls().add(nextQuestionsPage);
		// Create fields
		Field titleField = new Field(questionPage, "html body.question-page div.container div#content div div#question-header h1 a.question-hyperlink", "Title");
		Field answerField = new Field(questionPage, ".answer", ".post-text", "Answer");
		Field commentOfAnswerField = new Field(questionPage, ".comment-copy", "CommentOfAnswer");
		Field voteOfAnswerField = new Field(questionPage, ".vote-count-post", "VoteOfAnswer");
		Field isAcceptedAnswerField = new Field(questionPage, ".vote-accepted-on", "isAcceptedOfAnswer");
		Field tagsField = new Field(questionPage,
				"html body.question-page div.container div#content div div#mainbar div#question.question table tbody tr td.postcell div div.post-taglist",
				"Tags");
		Field questionField = new Field(questionPage, "html body.question-page div.container div#content div div#mainbar div#question.question",
				"table tbody tr td.postcell div div.post-text", "Question");
		answerField.setManyElements(true);
		commentOfAnswerField.setManyElements(true);

		commentOfAnswerField.setParentField(answerField);
		answerField.getSubFields().add(commentOfAnswerField);

		voteOfAnswerField.setParentField(answerField);
		answerField.getSubFields().add(voteOfAnswerField);

		isAcceptedAnswerField.setParentField(answerField);
		answerField.getSubFields().add(isAcceptedAnswerField);

		tagsField.setManyElements(true);
		tagsField.setSelectorForParentElement(true);

		// Add fields to urls pattern
		questionPage.getFields().add(titleField);
		questionPage.getFields().add(tagsField);
		questionPage.getFields().add(questionField);
		questionPage.getFields().add(answerField);
		// add urls to the web site
		website.setStartPage(questionsHome.getUrlPattern());
		website.getPatterns().add(questionsHome);
		website.getPatterns().add(nextQuestionsPage);
		website.getPatterns().add(questionPage);
		// add excluded urls to the web site
		website.getExcludedPatterns().add(excludedPat1);
		// save website
		websiteDao.persist(website);

		return website;
	}

}
