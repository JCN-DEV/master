'use strict';

angular.module('stepApp')
    .factory('InstEmplWithhelHistSearch', function ($resource) {
        return $resource('api/_search/instEmplWithhelHists/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
