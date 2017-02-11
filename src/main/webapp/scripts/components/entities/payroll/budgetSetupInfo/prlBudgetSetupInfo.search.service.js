'use strict';

angular.module('stepApp')
    .factory('PrlBudgetSetupInfoSearch', function ($resource) {
        return $resource('api/_search/prlBudgetSetupInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
