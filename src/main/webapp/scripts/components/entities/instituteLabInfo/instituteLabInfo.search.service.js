'use strict';

angular.module('stepApp')
    .factory('InstituteLabInfoSearch', function ($resource) {
        return $resource('api/_search/instituteLabInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
