'use strict';

angular.module('stepApp')
    .factory('HrEmpGovtDuesInfoSearch', function ($resource) {
        return $resource('api/_search/hrEmpGovtDuesInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
