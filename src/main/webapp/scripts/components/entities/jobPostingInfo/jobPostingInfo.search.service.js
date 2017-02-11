'use strict';

angular.module('stepApp')
    .factory('JobPostingInfoSearch', function ($resource) {
        return $resource('api/_search/jobPostingInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
