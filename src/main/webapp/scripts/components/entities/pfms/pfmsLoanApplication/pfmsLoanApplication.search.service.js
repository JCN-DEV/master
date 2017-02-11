'use strict';

angular.module('stepApp')
    .factory('PfmsLoanApplicationSearch', function ($resource) {
        return $resource('api/_search/pfmsLoanApplications/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
