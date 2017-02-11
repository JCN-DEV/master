'use strict';

angular.module('stepApp')
    .factory('DlBookCatSearch', function ($resource) {
        return $resource('api/_search/dlBookCats/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
