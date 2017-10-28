package com.Assignment3.Utilities;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.util.AttributeFactory;

class MyAnalyzer extends Analyzer {
      

	@Override
	protected TokenStreamComponents createComponents(String paramString) {
		AttributeFactory factory = AttributeFactory.DEFAULT_ATTRIBUTE_FACTORY;
        Tokenizer tokenizer = new StandardTokenizer(factory);
        tokenizer.setReader(new StringReader(paramString));
        TokenStream filter = new PorterStemFilter(tokenizer);
        filter = new LowerCaseFilter(filter);
        return new TokenStreamComponents(tokenizer, filter);
	}
    }
    