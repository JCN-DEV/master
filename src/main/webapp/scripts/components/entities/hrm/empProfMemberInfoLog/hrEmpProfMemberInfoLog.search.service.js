'use strict';

angular.module('stepApp')
    .factory('HrEmpProfMemberInfoLogSearch', function ($resource) {
        return $resource('api/_search/hrEmpProfMemberInfoLogs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
