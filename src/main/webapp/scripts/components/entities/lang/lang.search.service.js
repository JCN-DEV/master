'use strict';

angular.module('stepApp')
    .factory('LangSearch', function ($resource) {
        return $resource('api/_search/langs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('LangEmployee', function ($resource) {
        return $resource('api/langs/employee/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
