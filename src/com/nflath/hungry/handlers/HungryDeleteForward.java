package com.nflath.hungry.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.FindReplaceDocumentAdapter;
import org.eclipse.jface.text.IDocument;

import com.nflath.hungry.HungryUtils;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class HungryDeleteForward extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public HungryDeleteForward() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IDocument doc = HungryUtils.getCurrentDocument(event);
		FindReplaceDocumentAdapter searcher = new FindReplaceDocumentAdapter(doc);
		int start = HungryUtils.getCurrentOffset(event);
		int end = start;
		if (!HungryUtils.selectionDefined(event)) {
			while( end < searcher.length() &&
					Character.isWhitespace(searcher.charAt(end)) )	{
				end++;
			}
			if( end == start ) end++;
			if( end == searcher.length()) end--;
		} else {
			start= HungryUtils.getTextSelection(event).getOffset();
			end = HungryUtils.getTextSelection(event).getLength() + start;
		}
		try {
			doc.replace(start, end - start, "");
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
