'use strict';

describe('InstGovBodyAccessTemp Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstGovBodyAccessTemp, MockInstitute, MockUser;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstGovBodyAccessTemp = jasmine.createSpy('MockInstGovBodyAccessTemp');
        MockInstitute = jasmine.createSpy('MockInstitute');
        MockUser = jasmine.createSpy('MockUser');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstGovBodyAccessTemp': MockInstGovBodyAccessTemp,
            'Institute': MockInstitute,
            'User': MockUser
        };
        createController = function() {
            $injector.get('$controller')("InstGovBodyAccessTempDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instGovBodyAccessTempUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
