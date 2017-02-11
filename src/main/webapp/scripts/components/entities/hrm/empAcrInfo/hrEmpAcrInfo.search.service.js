'use strict';

angular.module('stepApp')
    .factory('HrEmpAcrInfoSearch', function ($resource) {
        return $resource('api/_search/hrEmpAcrInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
