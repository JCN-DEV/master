'use strict';

angular.module('stepApp')
    .factory('DlExistingBookIssueSearch', function ($resource) {
        return $resource('api/_search/dlExistingBookIssues/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
