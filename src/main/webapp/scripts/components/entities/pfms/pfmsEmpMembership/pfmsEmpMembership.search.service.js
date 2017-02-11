'use strict';

angular.module('stepApp')
    .factory('PfmsEmpMembershipSearch', function ($resource) {
        return $resource('api/_search/pfmsEmpMemberships/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
