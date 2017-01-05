/**
 * Copyright (c) 2016 Zerocracy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to read
 * the Software only. Permissions is hereby NOT GRANTED to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.zerocracy.crews.slack;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.zerocracy.crews.Question;
import com.zerocracy.crews.StkSafe;
import com.zerocracy.jstk.Farm;
import com.zerocracy.pm.StkByRoles;
import com.zerocracy.pm.hr.Alias;
import java.io.IOException;
import java.util.Arrays;

/**
 * Add an alias to the user.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.1
 */
final class ReAlias implements Reaction<SlackMessagePosted> {

    @Override
    public boolean react(final Farm farm, final SlackMessagePosted event,
        final SlackSession session) throws IOException {
        farm.deploy(
            new StkSafe(
                new SkPerson(event, session),
                new StkByRoles(
                    new SkProject(farm, event),
                    new SkPerson(event, session),
                    Arrays.asList("PO"),
                    new Alias(
                        farm.find("id=PMO").iterator().next(),
                        new SkPerson(event, session),
                        new Question(event.getMessageContent()).arg("rel"),
                        new Question(event.getMessageContent()).arg("href")
                    )
                )
            )
        );
        return true;
    }
}
