'use strict';

angular.module('stepApp')
    .factory('HrEmpServiceHistorySearch', function ($resource) {
        return $resource('api/_search/hrEmpServiceHistorys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
