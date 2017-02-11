'use strict';

angular.module('stepApp')
    .factory('BankAssignSearch', function ($resource) {
        return $resource('api/_search/bankAssigns/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
