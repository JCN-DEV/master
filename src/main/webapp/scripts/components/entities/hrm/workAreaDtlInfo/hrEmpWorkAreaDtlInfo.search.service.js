'use strict';

angular.module('stepApp')
    .factory('HrEmpWorkAreaDtlInfoSearch', function ($resource) {
        return $resource('api/_search/hrEmpWorkAreaDtlInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
