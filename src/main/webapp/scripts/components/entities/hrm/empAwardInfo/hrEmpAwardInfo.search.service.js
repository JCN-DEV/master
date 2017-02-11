'use strict';

angular.module('stepApp')
    .factory('HrEmpAwardInfoSearch', function ($resource) {
        return $resource('api/_search/hrEmpAwardInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
