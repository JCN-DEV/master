'use strict';

describe('TempEmployer Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockTempEmployer, MockUser, MockCountry;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockTempEmployer = jasmine.createSpy('MockTempEmployer');
        MockUser = jasmine.createSpy('MockUser');
        MockCountry = jasmine.createSpy('MockCountry');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'TempEmployer': MockTempEmployer,
            'User': MockUser,
            'Country': MockCountry
        };
        createController = function() {
            $injector.get('$controller')("TempEmployerDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:tempEmployerUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
