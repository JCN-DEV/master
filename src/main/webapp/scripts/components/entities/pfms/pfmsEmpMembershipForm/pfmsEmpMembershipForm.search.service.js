'use strict';

angular.module('stepApp')
    .factory('PfmsEmpMembershipFormSearch', function ($resource) {
        return $resource('api/_search/pfmsEmpMembershipForms/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
