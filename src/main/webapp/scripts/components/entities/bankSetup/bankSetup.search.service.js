'use strict';

angular.module('stepApp')
    .factory('BankSetupSearch', function ($resource) {
        return $resource('api/_search/bankSetups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
