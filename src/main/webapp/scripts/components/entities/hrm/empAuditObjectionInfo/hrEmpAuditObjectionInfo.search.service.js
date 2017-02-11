'use strict';

angular.module('stepApp')
    .factory('HrEmpAuditObjectionInfoSearch', function ($resource) {
        return $resource('api/_search/hrEmpAuditObjectionInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
