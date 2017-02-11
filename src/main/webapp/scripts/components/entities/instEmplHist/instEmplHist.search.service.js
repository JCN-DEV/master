'use strict';

angular.module('stepApp')
    .factory('InstEmplHistSearch', function ($resource) {
        return $resource('api/_search/instEmplHists/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
