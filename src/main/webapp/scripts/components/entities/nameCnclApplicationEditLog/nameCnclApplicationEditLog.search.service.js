'use strict';

angular.module('stepApp')
    .factory('NameCnclApplicationEditLogSearch', function ($resource) {
        return $resource('api/_search/nameCnclApplicationEditLogs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
