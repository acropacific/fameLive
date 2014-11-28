package com.famelive.common.slotManagement.rule

import com.famelive.common.command.RequestCommand
import com.famelive.common.enums.slotmanagement.GenreStatus
import com.famelive.common.exceptions.rule.CheckPublishedGenreRuleException
import com.famelive.common.exceptions.rule.RuleException
import com.famelive.common.slotmanagement.Genre

class CheckPublishedGenreRule implements Rule {

    @Override
    void apply(RequestCommand requestCommand) throws RuleException {
        List<Long> genreIds = requestCommand?.genreIds
        genreIds.each { Long id ->
            Genre genre = Genre.get(id)
            if (genre.status == GenreStatus.UN_PUBLISHED) {
                throw new CheckPublishedGenreRuleException()
            }
        }
    }
}
