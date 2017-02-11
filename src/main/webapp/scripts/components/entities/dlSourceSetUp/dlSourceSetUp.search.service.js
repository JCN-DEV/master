'use strict';

angular.module('stepApp')
    .factory('DlSourceSetUpSearch', function ($resource) {
        return $resource('api/_search/dlSourceSetUps/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
