'use strict';

angular.module('stepApp')
    .factory('DlBookIssueSearch', function ($resource) {
        return $resource('api/_search/dlBookIssues/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
