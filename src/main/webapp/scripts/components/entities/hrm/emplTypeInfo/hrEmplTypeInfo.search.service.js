'use strict';

angular.module('stepApp')
    .factory('HrEmplTypeInfoSearch', function ($resource) {
        return $resource('api/_search/hrEmplTypeInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
