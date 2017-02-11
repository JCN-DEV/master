'use strict';

angular.module('stepApp')
    .factory('HrEmpChildrenInfoLogSearch', function ($resource) {
        return $resource('api/_search/hrEmpChildrenInfoLogs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
