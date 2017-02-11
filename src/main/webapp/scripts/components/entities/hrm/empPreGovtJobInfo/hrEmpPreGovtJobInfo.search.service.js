'use strict';

angular.module('stepApp')
    .factory('HrEmpPreGovtJobInfoSearch', function ($resource) {
        return $resource('api/_search/hrEmpPreGovtJobInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
