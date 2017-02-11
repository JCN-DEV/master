'use strict';

angular.module('stepApp')
    .factory('InstituteInfraInfoSearch', function ($resource) {
        return $resource('api/_search/instituteInfraInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
