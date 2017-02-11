'use strict';

angular.module('stepApp')
    .factory('DlBookTypeSearch', function ($resource) {
        return $resource('api/_search/dlBookTypes/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
