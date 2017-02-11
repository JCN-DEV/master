'use strict';

angular.module('stepApp')
    .factory('DteJasperConfigurationSearch', function ($resource) {
        return $resource('api/_search/dteJasperConfigurations/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
