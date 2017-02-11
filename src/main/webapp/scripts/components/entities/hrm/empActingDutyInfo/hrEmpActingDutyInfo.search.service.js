'use strict';

angular.module('stepApp')
    .factory('HrEmpActingDutyInfoSearch', function ($resource) {
        return $resource('api/_search/hrEmpActingDutyInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
