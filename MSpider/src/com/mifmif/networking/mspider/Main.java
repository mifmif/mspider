/**
 * Copyright 2014 y.mifrah
 *

 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mifmif.networking.mspider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mifmif.networking.mspider.database.dao.api.WebsiteDao;
import com.mifmif.networking.mspider.database.dao.impl.JpaDaoFactory;
import com.mifmif.networking.mspider.model.DomainObjectModel;
import com.mifmif.networking.mspider.model.Field;
import com.mifmif.networking.mspider.model.URLParameter;
import com.mifmif.networking.mspider.model.URLPattern;
import com.mifmif.networking.mspider.model.Website;

/**
 * @author y.mifrah
 *
 */
public class Main {
	WebsiteDao websiteDao = JpaDaoFactory.getJpaWebsiteDao();
	private String QUESTION_TITLE_CSS_SELECTOR = "html body.question-page div.container div#content div div#question-header h1 a.question-hyperlink";
	private String QUESTION_CSS_SELECTOR = "html body.question-page div.container div#content div div#mainbar div#question.question";
	private String QUESTION_BODY_CSS_SELECTOR = "table tbody tr td.postcell div div.post-text";
	private String ANSWER_IS_ACCEPTED_CSS_SELECTOR = ".vote-accepted-on";
	private String ANSWER_VOTE_CSS_SELECTOR = ".vote-count-post";
	private String ANSWER_BODY_CSS_SELECTOR = ".post-text";
	private String ANSWER_CSS_SELECTOR = ".answer";
	private String ANSWER_COMMENT_CSS_SELECTOR = ".comment-copy";
	private String TAG_CSS_SELECTOR = "html body.question-page div.container div#content div div#mainbar div#question.question table tbody tr td.postcell div div.post-taglist";

	public static void main(String[] args) throws IOException {
		// System.setProperty("java.net.useSystemProxies", "true");
		Website website = new Main().bootstrapParamLoading();
		Engine engine = new Engine(website);
		// Engine engine = new Engine("http://www.wordpress-fr.net");
		engine.start();

	}

	/**
	 * Bootstrap Database by storing 'http://www.stackoverflow.com' sitemap as a
	 * sample in the DB for testing purpose : website with some urlPattern and
	 * fields and relation between them. we just focus on
	 * question/answer/vote/comment/tag fields
	 */
	Website bootstrap() {
		// Create website instance
		String host = "http://www.stackoverflow.com";
		Website website = new Website(host);
		// Create urls pattern
		URLPattern questionsHome = URLPattern.builder().withWebsite(website).withUrlPattern("/questions").withUrlName("HomePage").withContentStatic(false)
				.build();
		URLPattern nextQuestionsPage = URLPattern.builder().withWebsite(website).withUrlPattern("/questions\\?page=[0-9]+&sort=newest").withUrlName("NextPage")
				.withContentStatic(false).build();
		URLPattern questionPage = URLPattern.builder().withWebsite(website).withUrlPattern("/questions/[0-9]+/.+").withUrlName("QuestionPage")
				.withContentStatic(true).build();
		URLPattern excludedPat1 = URLPattern.builder().withUrlPattern("/questions\\?page=[0-9]{3,}&sort=newest").withExcludeFrom(website).build();

		// questionsHome mapping links to others pattern pages
		questionsHome.getNextUrls().add(questionPage);
		questionsHome.getNextUrls().add(nextQuestionsPage);

		// nextQuestionsPage mapping links to others pattern pages
		nextQuestionsPage.getNextUrls().add(questionPage);
		nextQuestionsPage.getNextUrls().add(nextQuestionsPage);

		// Create a domain object model
		DomainObjectModel questionModel = new DomainObjectModel(website, "Question");

		// Create fields
		Field titleField = new Field(questionModel, questionPage, QUESTION_TITLE_CSS_SELECTOR, "Title");
		Field questionField = new Field(questionModel, questionPage, QUESTION_CSS_SELECTOR, QUESTION_BODY_CSS_SELECTOR, "Question");
		Field answerField = new Field(questionModel, questionPage, ANSWER_CSS_SELECTOR, ANSWER_BODY_CSS_SELECTOR, "Answer");
		Field commentOfAnswerField = new Field(questionModel, questionPage, ANSWER_COMMENT_CSS_SELECTOR, "CommentOfAnswer");
		Field voteOfAnswerField = new Field(questionModel, questionPage, ANSWER_VOTE_CSS_SELECTOR, "VoteOfAnswer");
		Field isAcceptedAnswerField = new Field(questionModel, questionPage, ANSWER_IS_ACCEPTED_CSS_SELECTOR, "isAcceptedOfAnswer");
		Field tagsField = new Field(questionModel, questionPage, TAG_CSS_SELECTOR, "Tags");
		answerField.setManyElements(true);
		commentOfAnswerField.setManyElements(true);
		tagsField.setManyElements(true);
		tagsField.setSelectorForParentElement(true);

		// attach field to the domain model
		questionModel.getFields().add(titleField);
		questionModel.getFields().add(questionField);
		questionModel.getFields().add(tagsField);
		questionModel.getFields().add(answerField);// we didn't add
													// commentOfAnswerField and
													// isAcceptedAnswerField and
													// voteOfAnswerField because
													// they belong to the field
													// answer

		// attach subfields to their parent
		commentOfAnswerField.setParentField(answerField);
		answerField.getSubFields().add(commentOfAnswerField);

		voteOfAnswerField.setParentField(answerField);
		answerField.getSubFields().add(voteOfAnswerField);

		isAcceptedAnswerField.setParentField(answerField);
		answerField.getSubFields().add(isAcceptedAnswerField);

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

		// add Domain object model to the web site
		website.getObjectModels().add(questionModel);

		// save website
		websiteDao.persist(website);

		return website;
	}

	/**
	 * Bootstrap Database by storing 'http://www.stackoverflow.com' sitemap as a
	 * sample in the DB for testing purpose : website with some urlPattern and
	 * fields and relation between them. we just focus on
	 * question/answer/vote/comment/tag fields
	 */
	Website bootstrapParamLoading() {
		// Create website instance
		String host = "http://www.wordpress-fr.net";
		Website website = new Website(host);
		website.useParameterGenerationStrategy();
		URLPattern nodePage = URLPattern.builder().withWebsite(website).withUrlPattern("/support/viewtopic.php").withUrlName("DrupalNode")
				.withContentStatic(false).build();
		URLParameter idParameter = new URLParameter(nodePage, "id", "10[0-9]{2}");
		nodePage.getParameters().add(idParameter);
		// Create a domain object model
		DomainObjectModel nodeModel = new DomainObjectModel(website, "Thread");

		// Create fields
		Field titleField = new Field(nodeModel, nodePage, "title", "Title");

		// attach field to the domain model
		nodeModel.getFields().add(titleField);

		// Add fields to urls pattern
		nodePage.getFields().add(titleField);

		// add urls to the web site
		website.getPatterns().add(nodePage);

		// add Domain object model to the web site
		website.getObjectModels().add(nodeModel);

		// save website
		websiteDao.persist(website);

		return website;
	}

}
