'use strict';

describe('InstGovernBodyTemp Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstGovernBodyTemp, MockInstitute;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstGovernBodyTemp = jasmine.createSpy('MockInstGovernBodyTemp');
        MockInstitute = jasmine.createSpy('MockInstitute');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstGovernBodyTemp': MockInstGovernBodyTemp,
            'Institute': MockInstitute
        };
        createController = function() {
            $injector.get('$controller')("InstGovernBodyTempDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instGovernBodyTempUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
