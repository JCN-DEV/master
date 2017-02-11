'use strict';

angular.module('stepApp')
    .factory('HrEmploymentInfoLogSearch', function ($resource) {
        return $resource('api/_search/hrEmploymentInfoLogs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
