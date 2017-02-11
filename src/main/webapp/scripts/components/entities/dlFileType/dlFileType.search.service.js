'use strict';

angular.module('stepApp')
    .factory('DlFileTypeSearch', function ($resource) {
        return $resource('api/_search/dlFileTypes/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
