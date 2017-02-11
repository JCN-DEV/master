'use strict';

angular.module('stepApp')
    .factory('HrNomineeInfoSearch', function ($resource) {
        return $resource('api/_search/hrNomineeInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
