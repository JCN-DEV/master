'use strict';

angular.module('stepApp')
    .factory('InstEmplPayscaleHistSearch', function ($resource) {
        return $resource('api/_search/instEmplPayscaleHists/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
