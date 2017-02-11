'use strict';

angular.module('stepApp')
    .factory('HrEmpBankAccountInfoSearch', function ($resource) {
        return $resource('api/_search/hrEmpBankAccountInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
