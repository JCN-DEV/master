'use strict';

angular.module('stepApp')
    .factory('PrlEmpGeneratedSalInfoSearch', function ($resource) {
        return $resource('api/_search/prlEmpGeneratedSalInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
