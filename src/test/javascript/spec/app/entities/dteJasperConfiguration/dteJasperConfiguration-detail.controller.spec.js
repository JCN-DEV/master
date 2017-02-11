'use strict';

describe('DteJasperConfiguration Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockDteJasperConfiguration, MockUser;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockDteJasperConfiguration = jasmine.createSpy('MockDteJasperConfiguration');
        MockUser = jasmine.createSpy('MockUser');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'DteJasperConfiguration': MockDteJasperConfiguration,
            'User': MockUser
        };
        createController = function() {
            $injector.get('$controller')("DteJasperConfigurationDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:dteJasperConfigurationUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
